(function() {
    'use strict';

    angular
        .module('app')
        .controller('EcritureCompteAnalytiqueDialogController', EcritureCompteAnalytiqueDialogController);

    EcritureCompteAnalytiqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'EcritureCompteAnalytique','CompteAnalytique'];

    function EcritureCompteAnalytiqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, EcritureCompteAnalytique ,CompteAnalytique) {
        var vm = this;

        vm.ecritureCompteAnalytique = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.comptes = CompteAnalytique.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.ecritureCompteAnalytique.id !== null) {
                EcritureCompteAnalytique.update(vm.ecritureCompteAnalytique, onSaveSuccess, onSaveError);
            } else {
                EcritureCompteAnalytique.save(vm.ecritureCompteAnalytique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:ecritureCompteAnalytiqueUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
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
                            vm.ecritureCompteAnalytique[fieldName] = base64Data;
                            vm.ecritureCompteAnalytique[fieldName + 'ContentType'] = $file.type;
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
                        vm.ecritureCompteAnalytique[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
