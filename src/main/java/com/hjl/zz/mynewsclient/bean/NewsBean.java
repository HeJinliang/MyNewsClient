package com.hjl.zz.mynewsclient.bean;

import com.hjl.zz.mynewsclient.pager.NewsCenterPager;

import java.util.List;

/**
 *
 * 版权所有 违法必究
 *
 * @author hjl
 * @project MyNewsClient
 * @file ${FILE}
 * @create_time 2016/8/18
 * @github https://github.com/HeJinliang
 * =====================================
 */
public class NewsBean {

    public int retcode;
    public DataBean data;
    public class DataBean{
        public String countcommenturl;
        public String more;
        public List<NewsItemBean> news;
        public class NewsItemBean{
            public boolean comment;
            public String commentlist;
            public String commenturl;
            public int id;
            public String listimage;
            public String pubdate;
            public String title;
            public String type;
            public String url;
        }
        public String title;
        public List<TopicBean> topic;
        public class TopicBean{
            public String description;
            public int id;
            public String listimage;
            public int sort;
            public String title;
            public String url;
        }
        public List<TopnewsBean> topnews;
        public class TopnewsBean{
            public boolean comment;
            public String commentlist;
            public int id;
            public String pubdate;
            public String title;
            public String topimage;
            public String type;
            public String url;
        }
    }
}














