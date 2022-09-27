package test.optionspricing.services;

import org.springframework.stereotype.Service;

import test.optionspricing.dtos.CalcDto;

@Service
public class CalcService {

    public Double calc(CalcDto calcDto) {
        Double interest = calcDto.getInterest();
        Double price = calcDto.getPrice();
        int steps = calcDto.getSteps();
        boolean isEuropean = calcDto.isEuropean();
        boolean isPut = calcDto.isPut();
        int daysToExpiry= calcDto.getDaysToExpiry();
        Double sigma = calcDto.getSigma();

        Double timePerStep = Double.valueOf(daysToExpiry)/Double.valueOf(steps);
        Double u = Math.exp(sigma*Math.sqrt(timePerStep));
        System.out.println("u = " + u);
        Double d = 1/u;

        // construct binomial tree
        double[][][] tree = new double[steps + 1][steps + 1][2];
        tree[0][0][0] = price;
        for (int i = 1; i <= steps; i++) {
            for (int j = 0; j <= i; j++) {
                if (tree[i - 1][j][0] == 0) {
                    tree[i][j][0] = tree[i - 1][j - 1][0] * d;
                } else {
                    tree[i][j][0] = tree[i - 1][j][0] * u;
                }
            }
        }

        //initialise initial value of contracts
        for (int j = 0; j <= steps; j++) {
            tree[steps][j][1] = ((price - tree[steps][j][0])) < 0 ? 0 : (price - tree[steps][j][0]);
        }

        //calculate p
        double p = (Math.exp(interest/100*daysToExpiry/365/steps)-d)/(u-d);
        System.out.println("p = " + p);

        //reverse engineer value of contract
        for (int i = steps-1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                Double answer = (Math.exp(-interest/100*daysToExpiry/365/steps)*((p)*tree[i+1][j][1] + (1-p)*tree[i+1][j+1][1]));
                tree[i][j][1]= (answer < 0) ? 0 : answer;
            }
        }
        System.out.println("2nd calc: column: " + 0 + ", row: " + 0 + ", price: " + tree[0][0][0] + ", value: " + tree[0][0][1]);

        return tree[0][0][1];
    }
}
