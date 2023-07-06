package soldier;

public enum Rank {
    PRIVATE, CORPORAL, CAPTAIN, MAJOR;

    public Rank getNextRank() {
        Rank[] ranks = Rank.values();
        int nextOrdinal = this.ordinal() + 1;
        if (nextOrdinal < ranks.length) {
            return ranks[nextOrdinal];
        } else {
            return this;
        }
    }
}
