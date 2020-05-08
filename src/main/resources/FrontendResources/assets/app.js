Vue.component('todo-item', {
    template: '<p>To Do</p>'
});
var app = new Vue({
    el: '#app',
    data: {
        message: 'Hello Vue!'
    }
});