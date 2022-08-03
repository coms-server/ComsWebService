package coms.kw.ac.kr.server.controller.article;

/***
 * 게시글 리스트(board.jsp)에서 페이징을 위한 데이터 클래스.
 */
public class Pager {
    private Integer articleCount;
    private Integer currentPage;
    private Integer beginPage;
    private Integer endPage;

    private Boolean nextPageAvailable;
    private Boolean prevPageAvailable;
    private Integer nextPageTab;
    private Integer prevPageTab;
    private Integer veryFirstPage;
    private Integer veryLastPage;

    public Pager(int articleCount, int articlePerPage, int currentPage) {
        this.articleCount = articleCount;
        this.currentPage = currentPage;

        int maxPage = (int) ((articleCount - 1) / articlePerPage) + 1;
        this.beginPage = this.currentPage - 2 < 1 ? 1 : this.currentPage - 2;
        this.endPage = this.beginPage + 4 > maxPage ? maxPage : this.beginPage + 4;

        if (endPage == maxPage) {
            this.nextPageTab = maxPage;
            this.nextPageAvailable = false;
        } else {
            this.nextPageTab = endPage + 1;
            this.nextPageAvailable = true;
        }

        if (beginPage == 1) {
            this.prevPageTab = 1;
            this.prevPageAvailable = false;
        } else {
            this.prevPageTab = beginPage - 1;
            this.prevPageAvailable = true;
        }

        this.veryFirstPage = 1;
        this.veryLastPage = maxPage;
    }

    public Integer getArticleCount() {
        return this.articleCount;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public Integer getBeginPage() {
        return this.beginPage;
    }

    public Integer getEndPage() {
        return this.endPage;
    }

    public Boolean getNextPageAvailable() {
        return this.nextPageAvailable;
    }

    public Boolean getPrevPageAvailable() {
        return this.prevPageAvailable;
    }

    public Integer getNextPageTab() {
        return this.nextPageTab;
    }

    public Integer getPrevPageTab() {
        return this.prevPageTab;
    }

    public Integer getVeryFirstPage() {
        return this.veryFirstPage;
    }

    public Integer getVeryLastPage() {
        return this.veryLastPage;
    }
}
