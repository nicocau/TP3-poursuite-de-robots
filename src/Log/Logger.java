package log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Logger {
    /**
     * Le singleton
     */
    private static final Logger logger = new Logger();

    /**
     * Le chemin du fichier
     */
    private final String nomFichier = "./log.log";

    /**
     * L'encodage du fichier
     */
    private final String encodageFichier = "UTF-8";

    /**
     * Le format de la date
     */
    private final String formatDate = "[yyyy-MM-dd HH:mm:ss,SSS]";

    /**
     * Le s types de log authoriser
     */
    private final TypeLog[] typeLogsAuthoriser = {TypeLog.DEBUG, TypeLog.INFO, TypeLog.WARN, TypeLog.ERREUR, TypeLog.FATAL};

    /**
     * Le constructeur singleton, il sert a crée le fichier
     */
    private Logger() {
        Path source = Paths.get(this.nomFichier);
        if (!Files.exists(source)) {
            try {
                Files.createFile(source);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Permet de récuperer l'intance du logger
     *
     * @return Logger l'intance du logger
     */
    public static Logger getInstance() {
        return Logger.logger;
    }

    /**
     * Permet de vérifier si la log doit être inscrit dans le fichier
     *
     * @param typeLog
     * @return boolean
     */
    private boolean authoriseLaLog(TypeLog typeLog) {
        boolean res = false;
        for (TypeLog typeLogAuthoriser : this.typeLogsAuthoriser) {
            if (typeLog == typeLogAuthoriser) {
                res = true;
            }
        }
        return res;
    }

    /**
     * Permet d'ecrire une ligne dans le fichier
     *
     * @param date
     * @param typeLog
     * @param msg
     * @return
     */
    private boolean ecritUneLigne(String date, TypeLog typeLog, String msg) {
        boolean res = false;
        Path source = Paths.get(this.nomFichier);
        try {
            Files.write(source, Arrays.asList(date + "[" + typeLog + "] => " + msg), Charset.forName(this.encodageFichier), StandardOpenOption.APPEND);
            res = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * Methode a appeler pour ajouter une log
     *
     * @param typeLog
     * @param msg
     * @return
     */
    public boolean ajouteUneLigne(TypeLog typeLog, String msg) {
        boolean res = false;
        if (this.authoriseLaLog(typeLog)) {
            SimpleDateFormat format = new SimpleDateFormat(this.formatDate);
            Date date = new Date();
            String dateString = format.format(date);
            res = this.ecritUneLigne(dateString, typeLog, msg);
        }
        return res;
    }
}
