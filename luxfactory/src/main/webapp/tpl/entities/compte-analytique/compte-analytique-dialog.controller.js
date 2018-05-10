(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteAnalytiqueDialogController', CompteAnalytiqueDialogController);

    CompteAnalytiqueDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$uibModal','DataUtils', 'entity', 'CompteAnalytique','Tiers'];

    function CompteAnalytiqueDialogController ($timeout, $scope, $stateParams, $uibModalInstance,$uibModal, DataUtils, entity, CompteAnalytique ,Tiers) {
        var vm = this;

        vm.compteAnalytique = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tierss = Tiers.query();

      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.compteAnalytique.id !== null) {
                CompteAnalytique.update(vm.compteAnalytique, onSaveSuccess, onSaveError);
            } else {
                CompteAnalytique.save(vm.compteAnalytique, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:compteAnalytiqueUpdate', result);
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
                            vm.compteAnalytique[fieldName] = base64Data;
                            vm.compteAnalytique[fieldName + 'ContentType'] = $file.type;
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
                        vm.compteAnalytique[fieldname] = item;
                }, function() {
                    
                });
            };

    }
})();
