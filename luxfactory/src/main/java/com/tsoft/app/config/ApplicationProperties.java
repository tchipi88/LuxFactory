package com.tsoft.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Site site = new Site();

    public Site getSite() {
        return site;
    }

    public static class Site {

        private String nom;

        private String email;

        private String telephonePortable;

        private String telephoneFixe;

        private String siteWeb;

        private String boitePostale;

        private String adresse;
      
        private Float tva;
        
        private String logo;
        
      

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephonePortable() {
            return telephonePortable;
        }

        public void setTelephonePortable(String telephonePortable) {
            this.telephonePortable = telephonePortable;
        }

        public String getTelephoneFixe() {
            return telephoneFixe;
        }

        public void setTelephoneFixe(String telephoneFixe) {
            this.telephoneFixe = telephoneFixe;
        }

        public String getSiteWeb() {
            return siteWeb;
        }

        public void setSiteWeb(String siteWeb) {
            this.siteWeb = siteWeb;
        }

        public String getBoitePostale() {
            return boitePostale;
        }

        public void setBoitePostale(String boitePostale) {
            this.boitePostale = boitePostale;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        
        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public Float getTva() {
            return tva;
        }

        public void setTva(Float tva) {
            this.tva = tva;
        }

       
        
        

    }
}
