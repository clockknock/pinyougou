app.controller("baseController",function($scope){
    //分页控件配置currentPage:当前页   totalItems :总记录数  itemsPerPage:每页记录数
    //  perPageOptions :分页选项  onChange:当页码变更后自动触发的方法
    $scope.paginationConf = {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            // console.log("onChange");
            // $scope.reloadList();
        }
    };
    $scope.$watch("paginationConf.currentPage",function(){
        $scope.reloadList();
    });

    //将paginationConf的页数/页大小封装调用分页搜索
    $scope.reloadList = function () {
        $scope.search($scope.paginationConf.currentPage,
            $scope.paginationConf.itemsPerPage)
    };

    //新增/修改时清理弹出框中的内容
    $scope.clearEntity = function () {
        $scope.entity = {};
    };

    //修改商品--将之前请求下来的list中对应角标的brand放到$scope
    $scope.currentEntity = function (index) {
        $scope.entity = $scope.list[index];
    };

    //删除商品用的ids记录
    $scope.deleIds = [];

    //点击checkbox记录删除的id
    $scope.updateDeleteArrs = function (node, id) {
        if (node.checked) {
            $scope.deleIds.push(id);
        } else {
            var indexOf = $scope.deleIds.indexOf(id);
            $scope.deleIds.splice(indexOf, 1);
        }
    };

    //条件查询的实体对象
    $scope.searchEntity = {};

    //审核状态
    $scope.status = ["未审核", '审核通过', '审核未通过', '已关闭'];

    //所有商品分类
    $scope.findAllCat = function () {
        itemCatService.findAll().success(
            function (response) {
                $scope.allCat = [];
                for (var i = 0; i < response.length; i++) {
                    $scope.allCat[response[i].id] = response[i].name;
                }
            }
        )
    };

});