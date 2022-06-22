Vue.createApp({
    data() {
        return {
        stringUpper: "",
        client: {},
        cardType: "",
        cardColor:""
        }
    },

    created(){
        axios.get(`http://localhost:8080/api/clients/current`)
        .then(result =>{
            this.client = result.data
            }
        )
    },

    methods:{
        createCard (){
            if(this.cardType != "" && this.cardColor != ""){
                axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardColor}`,{headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(response => {
                
                        Swal.fire( {
                            icon: 'success',
                            text: 'Tarjeta Creada'
                        })
                        .then(res=> window.location.href = "/web/cards.html", 5000)
                })
                .catch (error => {
                    Swal.fire({
                        icon:'error',
                        text: error.response.data,
                        footer: '<a href="./create-cards.html">Volver a intentar</a>'
                    })
                })
            }else {
                Swal.fire({
                    icon: 'error',
                    text: 'Seleccione una opcion en todos los campos',
                    footer: '<a href="./create-cards.html">Volver a intentar</a>'
                })
            }
        },
        primerLetraToUpper (string) {
            this.stringUpper = string.charAt(0).toUpperCase() + string.slice(1)
            return this.stringUpper
        },
        logoUt(){
            axios.post('/api/logout').then(response =>  window.location.href = "/web/index.html")
        }
    },
    computed : {
    }

    }).mount('#app')