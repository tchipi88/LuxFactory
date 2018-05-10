(function() {
    'use strict';

    angular
        .module('app')
        .controller('DecaissementDeleteController',DecaissementDeleteController);

    DecaissementDeleteController.$inject = ['$uibModalInstance', 'entity', 'Decaissement'];

    function DecaissementDeleteController($uibModalInstance, entity, Decaissement) {
        var vm = this;

        vm.decaissement = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Decaissement.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
