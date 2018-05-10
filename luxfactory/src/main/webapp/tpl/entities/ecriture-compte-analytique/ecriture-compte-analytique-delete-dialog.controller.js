(function() {
    'use strict';

    angular
        .module('app')
        .controller('EcritureCompteAnalytiqueDeleteController',EcritureCompteAnalytiqueDeleteController);

    EcritureCompteAnalytiqueDeleteController.$inject = ['$uibModalInstance', 'entity', 'EcritureCompteAnalytique'];

    function EcritureCompteAnalytiqueDeleteController($uibModalInstance, entity, EcritureCompteAnalytique) {
        var vm = this;

        vm.ecritureCompteAnalytique = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EcritureCompteAnalytique.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
