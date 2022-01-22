package yoongeonung.springbasic.order;

import yoongeonung.springbasic.member.Grade;
import yoongeonung.springbasic.member.Member;

public class RateDiscountPolicy implements DiscountPolicy{

    /**
     * 10% Discount
     */
    private final int DISCOUNT_PERCENT = 10;

    @Override
    public int discount(Member member, int price) {
        if (member.getGrade() == Grade.VIP) {
            return (price * DISCOUNT_PERCENT) / 100;
        }
        return 0;
    }
}