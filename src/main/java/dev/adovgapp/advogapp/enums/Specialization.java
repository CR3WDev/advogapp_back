package dev.adovgapp.advogapp.enums;

public enum Specialization {

    CRIMINAL_LAW(0,"Direito Penal"),
    CIVIL_LAW(1,"Direito Civil"),
    FAMILY_LAW(2,"Direito de Família"),
    EMPLOYMENT_LAW(3,"Direito do Trabalho"),
    BUSINESS_LAW(4,"Direito Empresarial"),
    ENVIRONMENTAL_LAW(5,"Direito Ambiental"),
    REAL_ESTATE_LAW(6,"Direito Imobiliário"),
    INTELLECTUAL_PROPERTY_LAW(7,"Propriedade Intelectual"),
    CONSUMER_RIGHTS_LAW(8,"Direitos do Consumidor"),
    BANKRUPTCY_LAW(9,"Falências"),
    HUMAN_RIGHTS_LAW(10,"Direitos Humanos"),
    HEALTH_LAW(11,"Direito da Saúde"),
    INFORMATION_TECHNOLOGY_LAW(12,"Tecnologia da Informação (TI)");

    private String type;
    private Integer code;

    Specialization(Integer code,String type) {
        this.code = code;
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public static Specialization getByCode(int code) {

        for (Specialization specialization : Specialization.values()) {
            if (specialization.code == code) {
                return specialization;
            }
        }
     throw new Error("Specialization not found: " + code);
    }
}
