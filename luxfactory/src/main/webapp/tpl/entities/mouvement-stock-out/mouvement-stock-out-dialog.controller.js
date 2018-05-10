(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockOutDialogController', MouvementStockOutDialogController);

    MouvementStockOutDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'MouvementStockOut'];

    function MouvementStockOutDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, MouvementStockOut ) {
        var vm = this;

        vm.mouvementStockOut = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.mouvementStockOut.id !== null) {
                MouvementStockOut.update(vm.mouvementStockOut, onSaveSuccess, onSaveError);
            } else {
                MouvementStockOut.save(vm.mouvementStockOut, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:mouvementStockOutUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateTransaction = false;

        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
         vm.setMimage = function ($file, fieldName) {
                if ($file && $file.$error === 'pattern') {
                    return;
                }
                if ($file) {
                    DataUtils.toBase64($file, function (base64Data) {
                        $scope.$apply(function () {
                            vm.mouvementStockOut[fieldName] = base64Data;
                            vm.mouvementStockOut[fieldName + 'ContentType'] = $file.type;
                        });
                    });
                }
            };
            
            vm.zoomColumn = function (lookupCtrl,lookupTemplate, fieldname, entity) {
                $uibModal.open({
                    templateUrl: 'tpl/entities/'+lookupTemplate+'/'+lookupTemplate+'-dialog.html',
                    controller: lookupCtrl+'DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return entity;
                        }
                    }
                }).result.then(function(item) {
                        vm.mouvementStockOut[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
