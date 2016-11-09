package com.example.day_ok_2;

import java.util.List;

/**
 * Created by samsung on 2016/10/31.
 */
public class ZGCBean {


    /**
     * stitle : “雪姨”也玩LOL？微博力挺Faker拿一血
     * sdate : 2016-10-30 21:00:07
     * type : 0
     * listStyle : 1
     * label :
     * joinPeople : 19864
     * url : http://game.zol.com.cn/611/6115495.html
     * id : 6115495
     * imgsrc : http://wuxian.fd.zol-img.com.cn/t_s170x300_q7/g5/M00/05/0D/ChMkJlgVuaCIZyQdAAC_3Bqq6DQAAXUTQExBv8AAL_0737.jpg
     * imgsrc2 : http://article.fd.zol-img.com.cn/t_s640x2000c4/g5/M00/05/0C/ChMkJlgVpuuIFjzLAAB_SObTDZAAAXUOAFgMToAAH9g676.jpg
     * comment_num : 35
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String stitle;
        private String imgsrc2;

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getImgsrc2() {
            return imgsrc2;
        }

        public void setImgsrc2(String imgsrc2) {
            this.imgsrc2 = imgsrc2;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "stitle='" + stitle + '\'' +
                    ", imgsrc2='" + imgsrc2 + '\'' +
                    '}';
        }
    }
}
