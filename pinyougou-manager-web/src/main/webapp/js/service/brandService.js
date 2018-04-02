app.service('brandService', function ($http) {
    this.findAll = function () {
        return $http.get("../brand/findAll.do");
    };
    this.findPage = function (page, size) {
        return $http.get("../brand/findPage.do?pageNum=" + page + "&pageSize=" + size);
    };
    this.save = function (entity) {
        return $http.post("../brand/add.do", entity);
    };
    this.update = function (entity) {
        return $http.post("../brand/update.do", entity);
    };
    this.dele = function (deleIds) {
        return $http.get("../brand/delete.do?ids=" + deleIds);
    };

    this.search=function(page,size,searchEntity){
        return $http.post("../brand/search.do?pageNum=" + page + "&pageSize=" +
            size, searchEntity);
    };

    //下拉列表数据
    this.selectOptionList=function(){
        return $http.get('../brand/selectOptionList.do');
    }

    this.findBrandList = function(){
        return $http.post("../brand/findBrandList.do")
    }

});