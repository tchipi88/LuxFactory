(function () {
    'use strict';

    angular
            .module('app')
            .controller('MouvementStockInController', MouvementStockInController);

    MouvementStockInController.$inject = ['$state', '$stateParams', 'MouvementStockIn', 'ParseLinks', 'AlertService', '$filter', 'pagingParams', 'Entrepot', 'Produit'];

    function MouvementStockInController($state, $stateParams, MouvementStockIn, ParseLinks, AlertService, $filter, pagingParams, Entrepot, Produit) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;

        vm.entrepots = Entrepot.query();
        vm.produits = Produit.query();

        vm.itemsPerPage = 20;
        vm.page = 1;
        vm.fromDate = new Date();
        vm.toDate = new Date();



        vm.search = search;


        function search() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);

            MouvementStockIn.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort(),
                produit: vm.produit.id,
                entrepot: vm.entrepot.id,
                fromDate: fromDate, toDate: toDate
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.commandes = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.onChangeDate();
        }

        function transition() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);
            $state.transitionTo($state.$current, {
                page: vm.page,
                produit: vm.produit.id,
                entrepot: vm.entrepot.id,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                fromDate: fromDate, toDate: toDate
            });
        }




    }
})();
