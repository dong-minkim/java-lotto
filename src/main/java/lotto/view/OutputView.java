package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.Statistics;

import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    private static final int LOTTO_PRICE = 1000;
    private static final int NO_WINNING_LOTTO = 0;
    private static final String PURCHASE_LOTTO = "\n%d개를 구매했습니다.\n";
    private static final String SHOW_STATISTICS = "\n당첨 통계\n---";
    private static final String WINNING_RESULT = "%d개 일치%s (%s원) - %d개\n";
    private static final String SECOND_WINNING_RESULT = "%d개 일치, 보너스 볼 일치 (%s원) - %d개\n";
    private static final String OPEN_BRACKET = "[";
    private static final String CLOSE_BRACKET = "]";
    private static final String SEPARATOR = ", ";

    public void printPurchaseLottoCount(int money) {
        System.out.printf(PURCHASE_LOTTO, money / LOTTO_PRICE);
    }

    public void printLottos(List<Lotto> lottos) {
        for (Lotto lotto : lottos) {
            printLotto(lotto);
        }
    }

    private void printLotto(Lotto lotto) {
        String lottoNumbers = lotto.getNumbers()
                .stream()
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining(SEPARATOR));
        System.out.println(OPEN_BRACKET + lottoNumbers + CLOSE_BRACKET);
    }

    public void printStatistics(Statistics statistics) {
        System.out.println(SHOW_STATISTICS);

        statistics.getLottosResult()
                .entrySet()
                .stream()
                .filter(rankEntry -> rankEntry.getKey().getMatchingCnt() != NO_WINNING_LOTTO)
                .forEach(rankEntry -> {
                    String bonusBall = "";
                    String reward = new DecimalFormat("###,###").format(rankEntry.getKey().getReward());
                    if (rankEntry.getKey().isBonusBall()) {
                        bonusBall = ", 보너스 볼 일치";
                    }
                    System.out.printf(WINNING_RESULT, rankEntry.getKey().getMatchingCnt(), bonusBall, reward, rankEntry.getValue());
                });
    }
}
