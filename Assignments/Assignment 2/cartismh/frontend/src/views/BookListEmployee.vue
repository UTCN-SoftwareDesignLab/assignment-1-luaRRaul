<template>
  <v-card>
    <v-card-title>
      Book Sales
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="books"
      :search="search"
      @click:row="editBook"
    ></v-data-table>
    <BookDialogEmployee
      :opened="dialogVisible"
      :book="selectedBook"
      @refresh="refreshList"
    ></BookDialogEmployee>
  </v-card>
</template>

<script>
import api from "../api";
import BookDialogEmployee from "@/components/BookDialogEmployee";

export default {
  name: "BookListEmployee",
  components: { BookDialogEmployee },
  data() {
    return {
      books: [],
      search: "",
      headers: [
        {
          text: "Title",
          align: "start",
          sortable: false,
          value: "title",
        },
        { text: "Author", value: "author" },
        { text: "Genre", value: "genre" },
        { text: "Quantity", value: "quantity" },
        { text: "Price", value: "price" },
      ],
      dialogVisible: false,
      selectedBook: {},
    };
  },
  methods: {
    editBook(book) {
      this.selectedBook = book;
      this.dialogVisible = true;
    },
    async refreshList() {
      this.dialogVisible = false;
      this.selectedBook = {};
      this.books = await api.books.allBooks();
    },
  },
  created() {
    this.refreshList();
  },
};
</script>

<style scoped></style>
