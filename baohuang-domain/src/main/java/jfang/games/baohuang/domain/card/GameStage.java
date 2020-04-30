package jfang.games.baohuang.domain.card;

import lombok.Getter;

/**
 * 一句游戏的各个阶段
 *  1. 初始，等待各玩家加入
 *  2. 洗牌，发牌
 *  3. 选主公，玩家选择叫保镖牌
 *  4. 起义，平民阵营选择是否起义
 *  5. 进行中，一手牌一手牌执行
 *  6. 结束，统计分数
 *  7. 阶段记分
 *
 * @author Jun
 * @date 2020/4/29
 */
public enum GameStage {
    INIT(1),
    SHUFFLE(2),
    SELECT(3),
    REVOLUTION(4),
    RUNNING(5),
    END(6),
    REPORT(7);

    @Getter
    private int value;

    GameStage(int value) {
        this.value = value;
    }
}
