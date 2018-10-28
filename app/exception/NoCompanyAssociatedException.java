package exception;

public class NoCompanyAssociatedException extends Exception{
    private final int companyId;

    public NoCompanyAssociatedException(int companyId) {
        this.companyId = companyId;
    }

    public int getCompanyId() {
        return companyId;
    }
}
