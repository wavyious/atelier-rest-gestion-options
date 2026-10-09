package utilities;
import utilities.Services;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

public final class Services {

    public static final OptionBusiness OPTIONS = new OptionBusiness();
    public static final EtudiantBusiness ETUDIANTS = new EtudiantBusiness();

    private Services() {}
}