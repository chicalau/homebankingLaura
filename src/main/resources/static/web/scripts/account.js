Vue.createApp({
    data() {
        return {
        MYPARAM :"",
        transacciones : [],
        posicion : 0,
        cuenta: {},
        transaccionesOrdenadas: [],
        }
    },

    created(){
        const URLPARAMS = new URLSearchParams(window.location.search)
        const MYPARAM= URLPARAMS.get(`id`)
        axios.get("http://localhost:8080/api/accounts/"+ MYPARAM)  
        .then(result =>{
            this.cuenta = result.data
            this.transacciones = result.data.transactions
        }
        )
    },

    methods: {
        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        },
    }, 

    computed: {
        ordenarPorId () {
            let transaccionesOrdenadas = []
            transaccionesOrdenadas = this.transacciones.filter(transa => transa.id).sort(function(a,b){return b.id - a.id})
            this.transacciones = transaccionesOrdenadas
        },
    }

    }).mount('#app')