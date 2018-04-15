app.controller("contentController",function($scope,contentService){
   $scope.findByCategoryId= function(categoryId){
       //存放不同目录的广告图片
        $scope.contentList=[];

       contentService.findByCategoryId(categoryId).success(
           function(response){
               //用id作为角标,把对应的数据存到对应角标
                $scope.contentList[categoryId] = response;
           }
       )
   }
});