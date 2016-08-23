package com.hjl.zz.mynewsclient.bean;

import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/16
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class NewsCenterBean {

    public int retcode;
    public List<Integer> extend;
    public List<NewsDataBean> data;
    public class NewsDataBean{
        public int id;
        public String title;
        public int type;
        public String url;
        public String url1;
        public String excurl;
        public String dayurl;
        public String weekurl;
        public List<ChildrenBean> children;
        public class ChildrenBean{
            public int id;
            public String title;
            public int type;
            public String url;

            @Override
            public String toString() {
                return "ChildrenBean{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", url='" + url + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "NewsDataBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    ", url1='" + url1 + '\'' +
                    ", excurl='" + excurl + '\'' +
                    ", dayurl='" + dayurl + '\'' +
                    ", weekurl='" + weekurl + '\'' +
                    ", children=" + children +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsCenterBean{" +
                "retcode=" + retcode +
                ", extend=" + extend +
                ", data=" + data +
                '}';
    }
}
