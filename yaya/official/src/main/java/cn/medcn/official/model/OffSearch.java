package cn.medcn.official.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * by create HuangHuibin 2017/11/15
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "t_off_search")
public class OffSearch {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;
    private String search;
    private String searchUser;
    private String searchType;
    private Date searchTime;
    private Integer times;


    public enum SearchType{
        YSJY(1, "药师建议"),
        YISJY(2, "医师建议"),
        DZXY(3, "对症下药");

        private Integer searchType;

        private String searchName;

        public Integer getSearchType() {
            return searchType;
        }

        public String getSearchName() {
            return searchName;
        }

        SearchType(Integer searchType, String searchName){
            this.searchType = searchType;
            this.searchName = searchName;
        }
    }

    public static void main(String[] args) {

        System.out.println(OffSearch.SearchType.YSJY.getSearchType());
    }

}
