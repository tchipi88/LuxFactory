(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseOuvertureDeleteController',CaisseOuvertureDeleteController);

    CaisseOuvertureDeleteController.$inject = ['$uibModalInstance', 'entity', 'CaisseOuverture'];

    function CaisseOuvertureDeleteController($uibModalInstance, entity, CaisseOuverture) {
        var vm = this;

        vm.caisseOuverture = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CaisseOuverture.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
