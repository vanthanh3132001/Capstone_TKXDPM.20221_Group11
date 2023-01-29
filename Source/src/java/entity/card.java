package entity;

import java.util.Date;

public class card {
        private String CreditCardNumber;
        private String BankName;
        private String FullName;
        private Date ExpireDate;
        private String SecurityCode;
        private int money;

        public card(String creditCardNumber, String bankName, String fullName, Date expireDate, String securityCode, int money) {
                CreditCardNumber = creditCardNumber;
                BankName = bankName;
                FullName = fullName;
                ExpireDate = expireDate;
                SecurityCode = securityCode;
                this.money = money;
        }

        public String getCreditCardNumber() {
                return CreditCardNumber;
        }

        public void setCreditCardNumber(String creditCardNumber) {
                CreditCardNumber = creditCardNumber;
        }

        public String getBankName() {
                return BankName;
        }

        public void setBankName(String bankName) {
                BankName = bankName;
        }

        public String getFullName() {
                return FullName;
        }

        public void setFullName(String fullName) {
                FullName = fullName;
        }

        public Date getExpireDate() {
                return ExpireDate;
        }

        public void setExpireDate(Date expireDate) {
                ExpireDate = expireDate;
        }

        public String getSecurityCode() {
                return SecurityCode;
        }

        public void setSecurityCode(String securityCode) {
                SecurityCode = securityCode;
        }

        public int getMoney() {
                return money;
        }

        public void setMoney(int money) {
                this.money = money;
        }
}
