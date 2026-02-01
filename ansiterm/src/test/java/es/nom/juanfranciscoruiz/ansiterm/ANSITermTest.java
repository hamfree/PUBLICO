package es.nom.juanfranciscoruiz.ansiterm;


import es.nom.juanfranciscoruiz.ansiterm.codes.ColorsAndStylesCodes;
import es.nom.juanfranciscoruiz.ansiterm.exceptions.ANSITermException;
import org.junit.jupiter.api.Test;

class ANSITermTest {

    @Test
    void setTerminalSize() {

    }

    @Test
    void setCursorPosition() {
    }

    @Test
    void getOsCall() {
    }

    @Test
    void getCosc() {
        try {
            ANSITerm ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        ColorsAndStylesCodes cosc = ANSITerm.getCosc();
        assert cosc != null;
    }

    @Test
    void setCosc() throws ANSITermException {
        ANSITerm ansiTerm = new ANSITerm();
        ColorsAndStylesCodes cosc = ANSITerm.getCosc();
        assert cosc != null;
    }

    @Test
    void getCSI() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCSI() != null;

    }

    @Test
    void setCSI() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCSI() != null;
    }

    @Test
    void getCcc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCcc() != null;
    }

    @Test
    void setCcc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCcc() != null;
    }

    @Test
    void getCmc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCmc() != null;
    }

    @Test
    void setCmc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCmc() != null;
    }

    @Test
    void getCsc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCsc() != null;
    }

    @Test
    void setCsc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCsc() != null;
    }

    @Test
    void getEsec() {
        ANSITerm ansiTerm;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ansiTerm.getEsec() != null;
    }

    @Test
    void setEsec() {
        ANSITerm ansiTerm;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ansiTerm.getEsec() != null;
    }

    @Test
    void getGac() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getGac() != null;
    }

    @Test
    void setGac() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getGac() != null;
    }

    @Test
    void getImcc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getImcc() != null;
    }

    @Test
    void setImcc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getImcc() != null;
    }

    @Test
    void getVpc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getVpc() != null;
    }

    @Test
    void setVpc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getVpc() != null;
    }

    @Test
    void getTc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getTc() != null;
    }

    @Test
    void setTc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getTc() != null;
    }

    @Test
    void getVposc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getVposc() != null;
    }

    @Test
    void setVposc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getVposc() != null;
    }

    @Test
    void getCsmc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCsmc() != null;
    }

    @Test
    void setCsmc() {
        ANSITerm ansiTerm = null;
        try {
            ansiTerm = new ANSITerm();
        } catch (ANSITermException e) {
            throw new RuntimeException(e);
        }
        assert ANSITerm.getCsmc() != null;
    }
}