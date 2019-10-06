package homework3;

class CountryThread implements Runnable {
    private CountryInfo countryInfo;

    CountryThread(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public void run() {
        countryInfo.getCapital();
    }
}
