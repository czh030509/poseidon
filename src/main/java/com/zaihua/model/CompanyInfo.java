package com.zaihua.model;

/**
 * @author : zaihua.chen
 * @version : 1.0.0
 * @since : 2016/12/29 11:27
 */
public class CompanyInfo {
    public Company getTqCompInfo() {
        return tqCompInfo;
    }

    public void setTqCompInfo(Company tqCompInfo) {
        this.tqCompInfo = tqCompInfo;
    }

    private Company tqCompInfo;

    public class Company {
        public String getCompsname() {
            return compsname;
        }

        public void setCompsname(String compsname) {
            this.compsname = compsname;
        }

        private String compsname;
    }
}
