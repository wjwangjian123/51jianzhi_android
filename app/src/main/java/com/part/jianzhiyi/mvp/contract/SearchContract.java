package com.part.jianzhiyi.mvp.contract;

import com.part.jianzhiyi.corecommon.base.view.IModel;
import com.part.jianzhiyi.corecommon.base.view.IView;
import com.part.jianzhiyi.model.base.ResponseData;
import com.part.jianzhiyi.model.entity.JobListResponseEntity;
import com.part.jianzhiyi.model.entity.LableEntity;
import com.part.jianzhiyi.modulemerchants.model.entity.SearchEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author:shixinxin
 * @content：内容
 * @date:
 * @vision:V1.0.1
 * @modified time:2019/10/12
 * @modified by:shixinxin
 **/
public interface SearchContract {
    interface ISearchModel extends IModel {
        Observable<ResponseData<List<JobListResponseEntity>>> jobSearch(String userid, String key);

        Observable<LableEntity> getLable();

        Observable<SearchEntity> search(String title, String lable);

        Observable<ResponseData> getaddMd(String type);
    }

    interface ISearchView extends IView {
        void updateList(List<JobListResponseEntity> list);

        void updategetLable(LableEntity lableEntity);

        void updatesearch(SearchEntity searchEntity);

        void updategetaddMd(ResponseData responseData);
    }
}
