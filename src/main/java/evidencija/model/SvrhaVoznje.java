package evidencija.model;

    public enum SvrhaVoznje {

        Službeno ("Službeno"),
        Privatno ("Privatno");

        private final String svrhaVoznje;

        SvrhaVoznje(String svrhaVoznje) {
            this.svrhaVoznje = svrhaVoznje;
        }

        public String getSvrhaVoznje() {
            return svrhaVoznje;
        }
    }

