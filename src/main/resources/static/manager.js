Vue.createApp({
    data() {
        return {
            arrayClientes:[],
            json:[],
            sinClientes: "",
            client:{},
            person:"",
            inputFirstName:"",
            inputLastName:"",
            inputEmail:"",
        }
    },
    created(){
        axios.get(`/api/clients`)
        .then(result => {
            this.arrayClientes = result.data;
            this.json = result.data;
            let x = document.getElementById("xx");
                if(this.arrayClientes.length == 0){
                x.style.display ="none"}
        })
    },
    methods: {
                agregarClientes(){
                    if (this.inputFirstName != "" && this.inputLastName != "" && this.inputEmail != ""){
                        this.client = {
                            firstName: this.inputFirstName,
                            lastName: this.inputLastName,
                            email: this.inputEmail
                        }
                        axios.post(`/rest/clients`, this.client)
                    }
                },
                eliminarCliente(clienteParametro){
                        this.person = `/rest/clients/${clienteParametro.id}`
                        console.log(this.person)
                        axios.delete(this.person)
                        //axios.delete(`http://localhost:8080/rest/clients/1`)
                        //axios.delete( `http://127.0.0.1:8000/api/clients`, clienteParametro.id) 
                        location.reload()


                        //axios.delete('https://reqres.in/api/posts/1', { headers })
                        //.then(() => element.innerHTML = 'Delete successful');


                        // eliminarCliente(clienteParametro){
                        //     this.cliente = clienteParametro._links.client.href;
                        //         axios.delete(this.cliente)
                        //         location.reload()
                        // }
                },
    },
    computed: {
                dibujarTablaClientes(){
                        if (this.arrayClientes.length == 0){
                            this.sinClientes = "Customer not registered. Add one."
                            this.arrayClientes.toString()
                            this.arrayClientes = this.sinClientes
                            return this.arrayClientes
                            }
                        }





    },
}).mount('#app')