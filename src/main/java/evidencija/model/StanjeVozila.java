package evidencija.model;

    public enum StanjeVozila {

        Neoštećeno ("oštećeno"),
        Oštećeno ("neoštećeno");

        private final String stanjeVozila;

        StanjeVozila(String stanjeVozila) {
            this.stanjeVozila = stanjeVozila;
        }

        public String getStanjeVozila() {
            return stanjeVozila;
        }
    }

