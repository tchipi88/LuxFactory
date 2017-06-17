(function() {
    'use strict';

    angular
        .module('app')
        .controller('EcritureCompteAnalytiqueDetailController', EcritureCompteAnalytiqueDetailController);

    EcritureCompteAnalytiqueDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'EcritureCompteAnalytique' ,'CompteAnalytique'];

    function EcritureCompteAnalytiqueDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, EcritureCompteAnalytique ,CompteAnalytique) {
        var vm = this;

        vm.ecritureCompteAnalytique = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:ecritureCompteAnalytiqueUpdate', function(event, result) {
            vm.ecritureCompteAnalytique = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
