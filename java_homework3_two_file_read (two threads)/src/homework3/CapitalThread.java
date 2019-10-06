package homework3;

class CapitalThread implements Runnable {
    private CountryInfo countryInfo;

    CapitalThread(CountryInfo countryInfo) {
        this.countryInfo = countryInfo;
    }

    public void run() {
        countryInfo.getCountry();
    }
}
