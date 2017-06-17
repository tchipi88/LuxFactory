(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseOuvertureDialogController', CaisseOuvertureDialogController);

    CaisseOuvertureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'CaisseOuverture','Caisse'];

    function CaisseOuvertureDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, CaisseOuverture ,Caisse) {
        var vm = this;

        vm.caisseOuverture = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.caisses = Caisse.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.caisseOuverture.id !== null) {
                CaisseOuverture.update(vm.caisseOuverture, onSaveSuccess, onSaveError);
            } else {
                CaisseOuverture.save(vm.caisseOuverture, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:caisseOuvertureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


         vm.datePickerOpenStatus.dateDebut = false;
 vm.datePickerOpenStatus.dateFin = false;

        
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
                            vm.caisseOuverture[fieldName] = base64Data;
                            vm.caisseOuverture[fieldName + 'ContentType'] = $file.type;
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
                        vm.caisseOuverture[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
