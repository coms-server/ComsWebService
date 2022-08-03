package coms.kw.ac.kr.server.vo.statics;

/**
 * Container class containing {@code board}'s information.
 * Each board must must have unique {@code context_path} (it's Unique constraint).
 */
public interface Board {
    public boolean isRoot();

    public int getIndex();

    public String getContext();

    public String getName();

    public boolean isQna();

    public boolean isImportant();
}