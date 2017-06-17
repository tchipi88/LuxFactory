(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueDetailController', CompteAnalytiqueDetailController);

    CompteAnalytiqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CompteAnalytique' ,'Tiers'];

    function CompteAnalytiqueDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CompteAnalytique ,Tiers) {
        var vm = this;

        vm.compteAnalytique = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:compteAnalytiqueUpdate', function(event, result) {
            vm.compteAnalytique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
