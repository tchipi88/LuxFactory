(function () {
    'use strict';

    angular
            .module('app')
            .controller('CommandeController', CommandeController);

    CommandeController.$inject = ['$state', '$stateParams', '$filter', 'Commande', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function CommandeController($state, $stateParams, $filter, Commande, ParseLinks, AlertService, paginationConstants, pagingParams) {

        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;



        vm.itemsPerPage = 20;
        vm.page = 1;


        vm.fromDate = new Date();
        vm.toDate = new Date();

        vm.onChangeDate = onChangeDate;

        vm.onChangeDate();

        function onChangeDate() {
            var dateFormat = 'yyyy-MM-dd';
            var fromDate = $filter('date')(vm.fromDate, dateFormat);
            var toDate = $filter('date')(vm.toDate, dateFormat);

            Commande.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort(),
                type: $stateParams.type,
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
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                fromDate: fromDate, toDate: toDate
            });
        }




    }
})();
