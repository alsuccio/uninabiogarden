package org.uninabiogarden.oobd68.entity;

import org.uninabiogarden.oobd68.dao.AttivitaDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attivita {
    private ArrayList<ColtivazioneStagionale> svoltaInColtivazioneStagionale;
    private ArrayList<Lotto> svoltaInLotto;
    private ArrayList<Notifica> generaNotifica;
    private ArrayList<Utente> pianificata;
    private ArrayList<Utente> svoltada;
    private LocalDateTime iniziosemina;
    private LocalDateTime finesemina;
    private LocalDateTime inizioraccolta;
    private LocalDateTime fineraccolta;
    private LocalDateTime inizioirrigazione;
    private LocalDateTime fineirrigazione;
    private Stato tipoStato;
    private Categoria tipoCategoria;
    private int idAttivita;
    private double quantitaPrevista;
    private double quantitaRaccolta;

    public Attivita() {
        this.svoltaInColtivazioneStagionale = new ArrayList<>();
        this.svoltaInLotto = new ArrayList<>();
        this.generaNotifica = new ArrayList<>();
        this.pianificata = new ArrayList<>();
        this.svoltada = new ArrayList<>();
    }

    public Attivita(LocalDateTime iniziosemina, LocalDateTime finesemina, LocalDateTime inizioraccolta, LocalDateTime fineraccolta, LocalDateTime inizioirrigazione, LocalDateTime fineirrigazione, Stato tipoStato, int idattivita, double quantitaPrevista, double quantitaRaccolta) {
        this.svoltaInColtivazioneStagionale = new ArrayList<>();
        this.svoltaInLotto = new ArrayList<>();
        this.generaNotifica = new ArrayList<>();
        this.pianificata = new ArrayList<>();
        this.svoltada = new ArrayList<>();
        this.iniziosemina = iniziosemina;
        this.finesemina = finesemina;
        this.inizioraccolta = inizioraccolta;
        this.fineraccolta = fineraccolta;
        this.inizioirrigazione = inizioirrigazione;
        this.fineirrigazione = fineirrigazione;
        this.tipoStato = tipoStato;
        this.tipoCategoria = tipoCategoria;
        this.idAttivita = idattivita;
        this.quantitaPrevista = quantitaPrevista;
        this.quantitaRaccolta = quantitaRaccolta;
    }

    public ArrayList<ColtivazioneStagionale> getSvoltaInColtivazioneStagionale() {
        return svoltaInColtivazioneStagionale;
    }

    public void addSvoltaInColtivazioneStagionale(ColtivazioneStagionale coltivazioneStagionale) {
        if (!this.svoltaInColtivazioneStagionale.add(coltivazioneStagionale)) {
            this.svoltaInColtivazioneStagionale.add(coltivazioneStagionale);
        }
    }

    public void removeSvoltaInColtivazioneStagionale(ColtivazioneStagionale coltivazioneStagionale) {
        this.svoltaInColtivazioneStagionale.add(coltivazioneStagionale);
    }

    public void setSvoltaInColtivazioneStagionale(ArrayList<ColtivazioneStagionale> svoltaInColtivazioneStagionale) {
        this.svoltaInColtivazioneStagionale = svoltaInColtivazioneStagionale;
    }

    public ArrayList<Lotto> getSvoltaInLotto() {
        return svoltaInLotto;
    }

    public void addSvoltaInLotto(Lotto lotto) {
        if (!this.svoltaInLotto.add(lotto)) {
            this.svoltaInLotto.add(lotto);
        }
    }

    public void removeSvoltaInLotto(Lotto lotto) {
        this.svoltaInLotto.remove(lotto);
    }

    public void setSvoltaInLotto(ArrayList<Lotto> svoltaInLotto) {
        this.svoltaInLotto = svoltaInLotto;
    }

    public ArrayList<Notifica> getGeneraNotifica() {
        return generaNotifica;
    }

    public void addGeneraNotifica(Notifica notifica) {
        if (!this.generaNotifica.add(notifica)) {
            this.generaNotifica.add(notifica);
        }
    }

    public void removeGeneraNotifica(Notifica notifica) {
        this.generaNotifica.add(notifica);
    }

    public void setGeneraNotifica(ArrayList<Notifica> generaNotifica) {
        this.generaNotifica = generaNotifica;
    }

    public ArrayList<Utente> getPianificata() {
        return this.pianificata;
    }

    public void addPianificata(Utente utente) {
        if (!this.pianificata.contains(utente)) {
            this.pianificata.add(utente);
        }
    }

    public void removePianificata(Utente utente) {
        this.pianificata.remove(utente);
    }

    public void setPianificata(ArrayList<Utente> pianificata) {
        this.pianificata = pianificata;
    }

    public ArrayList<Utente> getSvoltada() {
        return this.svoltada;
    }

    public void addSvoltada(Utente utente) {
        if (!this.svoltada.contains(utente)) {
            this.svoltada.add(utente);
        }
    }

    public void removeSvoltada(Utente utente) {
        this.svoltada.remove(utente);
    }

    public void setSvoltada(ArrayList<Utente> svoltada) {
        this.svoltada = svoltada;
    }

    public LocalDateTime getinizioSemina() {
        return iniziosemina;
    }

    public void setinizioSemina(LocalDateTime iniziosemina) {
        this.iniziosemina = iniziosemina;
    }

    public LocalDateTime getfineSemina() {
        return finesemina;
    }

    public void setfineSemina(LocalDateTime finesemina) {
        this.finesemina = finesemina;
    }

    public LocalDateTime getinizioRaccolta() {
        return inizioraccolta;
    }

    public void setinizioRaccolta(LocalDateTime inizioraccolta) {
        this.inizioraccolta = inizioraccolta;
    }

    public LocalDateTime getfineRaccolta() {
        return fineraccolta;
    }

    public void setfineRaccolta(LocalDateTime fineraccolta) {
        this.fineraccolta = fineraccolta;
    }

    public void impostaDettagliRaccolta(double quantitaPrevista) {
        if (this.tipoCategoria != Categoria.RACCOLTA) {
            throw new IllegalArgumentException("Errore categoria: " + this.tipoCategoria);
        }

        if (quantitaPrevista <= 0) {
            throw new IllegalArgumentException("Quantità non valida");
        }
        this.quantitaPrevista = quantitaPrevista;
    }

    public void registraRaccolta(double quantitaEffettiva) {
        if (this.tipoCategoria != Categoria.RACCOLTA) {
            throw new IllegalStateException("Errore categoria: " + this.tipoCategoria);
        }
        this.quantitaRaccolta = quantitaEffettiva;
        this.tipoStato = Stato.COMPLETATO;
        this.fineraccolta = LocalDateTime.now();
    }

    public List<Attivita> recuperaAttivitaPerProgetto(int idColtivazione) {
        AttivitaDAO dao = new AttivitaDAO();
        return dao.recuperaAttivitaPerProgetto(idColtivazione);
    }

    public LocalDateTime getinizioIrrigazione() {
        return inizioirrigazione;
    }

    public void setinizioIrrigazione(LocalDateTime inizioirrigazione) {
        this.inizioirrigazione = inizioirrigazione;
    }

    public LocalDateTime getfineIrrigazione() {
        return fineirrigazione;
    }

    public void setfineIrrigazione(LocalDateTime fineirrigazione) {
        this.fineirrigazione = fineirrigazione;
    }

    public Stato getTipoStato() {
        return tipoStato;
    }

    public void setTipoStato(Stato tipoStato) {
        this.tipoStato = tipoStato;
    }

    public Categoria getTipoCategoria() {
        return tipoCategoria;
    }

    public void setTipoCategoria(Categoria tipoCategoria) {
        this.tipoCategoria = tipoCategoria;
    }

    public int getidAttivita() {
        return idAttivita;
    }

    public void setidAttivita(int idattivita) {
        this.idAttivita = idattivita;
    }

    public double getQuantitaPrevista() {
        return quantitaPrevista;
    }

    public void setQuantitaPrevista(double quantitaPrevista) {
        this.quantitaPrevista = quantitaPrevista;
    }

    public double getQuantitaRaccolta() {
        return quantitaRaccolta;
    }

    public void setQuantitaRaccolta(double quantitaRaccolta) {
        this.quantitaRaccolta = quantitaRaccolta;
    }
}
