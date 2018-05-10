(function() {
    'use strict';

    angular
        .module('app')
        .controller('CommandeLigneDeleteController',CommandeLigneDeleteController);

    CommandeLigneDeleteController.$inject = ['$uibModalInstance', 'entity', 'CommandeLigne'];

    function CommandeLigneDeleteController($uibModalInstance, entity, CommandeLigne) {
        var vm = this;

        vm.commandeLigne = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CommandeLigne.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
