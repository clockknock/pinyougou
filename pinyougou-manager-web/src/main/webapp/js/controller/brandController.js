app.controller('brandController', function ($scope, $controller, brandService) {
    //继承baseController
    $controller('baseController', {$scope: $scope});

    //查询所有商品列表
    $scope.findAll = function () {
        brandService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    };

    //分页搜索
    $scope.findPage = function (page, size) {
        brandService.findPage(page, size).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            }
        )
    };

    //新增商品
    $scope.save = function () {
        var obj;
        if ($scope.entity.id == null) {
            obj = brandService.save($scope.entity);
        } else {
            obj = brandService.update($scope.entity);
        }
        obj.success(
            function (response) {
                $scope.reloadList();
            }
        );
        $scope.clearEntity();

    };

    //删除记录ids对应的品牌
    $scope.dele = function () {
        if (confirm("确定要删除品牌吗?")) {
            brandService.dele($scope.deleIds).success(
                function (response) {
                    if (response.status) {

                        $scope.reloadList();
                    } else {
                        alert(response.msg)
                    }
                }
            )
        }
    };

    //搜索
    $scope.search = function (page, size) {
        brandService.search(page, size, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;
            }
        )
    };

})