package org.uninabiogarden.oobd68.controller;

import org.uninabiogarden.oobd68.boundary.*;
import org.uninabiogarden.oobd68.dao.*;
import org.uninabiogarden.oobd68.entity.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private UtenteDAO utenteDAO;
    private LottoDAO lottoDAO;
    private ColturaDAO coltureDAO;
    private AttivitaDAO attivitaDAO;
    private NotificaDAO notificaDAO;
    private ColtivazioneStagionaleDAO coltivazioneDAO;
    private Attivita attivitaCorrente;
    private Notifica notificaCorrente;

    public Controller() {
        DbConnection.testConnection();
        this.utenteDAO = new UtenteDAO(this);
        this.lottoDAO = new LottoDAO(this);
        this.coltureDAO = new ColturaDAO(this);
        this.attivitaDAO = new AttivitaDAO(this);
        this.notificaDAO = new NotificaDAO(this);
        this.coltivazioneDAO = new ColtivazioneStagionaleDAO(this);
    }

    public Utente PublicUtenteNome(String username, String password) {
        return utenteDAO.login(username, password);
    }

    public boolean CreaProgettoStagionale(Lotto lottoselezionato, List<Coltura> colturescelte) {
        return utenteDAO.creaProgettoStagionale(lottoselezionato, colturescelte);
    }

    public boolean IsTerrenoAdatto(int idLotto, Coltura coltura) {
        return lottoDAO.isTerrenoAdatto(idLotto, coltura);
    }

    public String getStagioneMigliore(int idColtura) {
        return coltureDAO.getStagioneMigliore(idColtura);
    }

    public void AggiungiAttività(Attivita nuovaAttivita) {
        int idColtivazioneCorrente = 1;
        coltivazioneDAO.aggiungiAttivita(idColtivazioneCorrente, nuovaAttivita);
    }

    public void RegistraRaccolta(double QuantitaRaccolta) {
        if (this.attivitaCorrente != null) {
            attivitaDAO.registraRaccolta(this.attivitaCorrente.getidAttivita(), QuantitaRaccolta);
        }
    }

    public void ImpostaDettagliRaccolta(double QuantitaPrevista) {
        if (this.attivitaCorrente != null) {
            attivitaDAO.impostaDettagliRaccolta(this.attivitaCorrente.getidAttivita(), QuantitaPrevista);
        }
    }

    public void InserisciNotifica() {
        if (this.notificaCorrente != null) {
            notificaDAO.inserisciNotifica(this.notificaCorrente);
        }
    }

    public void ContrassegnaComeLetta() {
        if (this.notificaCorrente != null) {
            notificaDAO.contrassegnaComeLetta(this.notificaCorrente.getidNotifica());
        }
    }

    public void InviaNotificaManuale(String testo, Messaggio tipo, int idLotto, int idColtivatoreTarget) {
        if (idColtivatoreTarget == 0) {
            List<Utente> collaboratori = new ArrayList<>();
            System.out.println("Invio notifica a TUTTI i collaboratori del lotto " + idLotto);

            for (Utente clt : collaboratori) {
                Notifica n = new Notifica();
                n.setContenuto(testo);
                n.setTipoMessaggio(tipo);
                n.setProblema("Segnalazione Broadcast");
                notificaDAO.inserisciNotifica(n);
            }

            Notifica nGenerica = new Notifica();
            nGenerica.setContenuto(testo + " (A TUTTI)");
            nGenerica.setTipoMessaggio(tipo);
            notificaDAO.inserisciNotifica(nGenerica);

        } else {
            Notifica n = new Notifica();
            n.setContenuto(testo);
            n.setTipoMessaggio(tipo);
            n.setProblema("Messaggio Diretto a ID: " + idColtivatoreTarget);

            notificaDAO.inserisciNotifica(n);
            System.out.println("Notifica inviata al singolo coltivatore ID: " + idColtivatoreTarget);
        }
    }

    public List<Attivita> RecuperaAttivitaPerProgetto(int idColtivazione) {
        return attivitaDAO.recuperaAttivitaPerProgetto(idColtivazione);
    }

    public void visualizzaReport(int idLotto) {
        List<ReportDati> dati = attivitaDAO.generaReport(idLotto);

        if (dati.isEmpty()) {
            System.out.println("Nessun dato di raccolta disponibile per il lotto " + idLotto);
        }

        new ReportBoundary(dati, idLotto).setVisible(true);
    }
    public void setAttivitaCorrente(Attivita a) { this.attivitaCorrente = a; }
    public void setNotificaCorrente(Notifica n) { this.notificaCorrente = n; }
}