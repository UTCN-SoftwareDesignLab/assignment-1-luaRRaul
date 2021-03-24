package model.builder;

import model.Right;

public class RightBuilder {

    private final Right right;

    public RightBuilder() {
        right = new Right();
    }

    public RightBuilder setName(String name) {
        right.setRight(name);
        return this;
    }

    public RightBuilder setId(long id) {
        right.setId(id);
        return this;
    }

    public Right build() {
        return this.right;
    }
}
