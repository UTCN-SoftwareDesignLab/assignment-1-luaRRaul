<template>
  <v-dialog
      transition="dialog-bottom-transition"
      max-width="600"
      :value="opened"
  >
    <template>
      <v-card>
        <v-toolbar color="primary" dark>
        </v-toolbar>
        <v-card-actions>
          <v-btn @click="sell">
            Sell
          </v-btn>
        </v-card-actions>
      </v-card>
    </template>
  </v-dialog>
</template>

<script>
import api from "../api";

export default {
  name: "BookDialogEmployee",
  props: {
    book: Object,
    opened: Boolean,
  },
  methods: {
    sell(){
      api.books.sell(
          {
            id: this.book.id,
            title: this.book.title,
            author: this.book.author,
            genre: this.book.genre,
            quantity: this.book.quantity,
            price: this.book.price,
          }
      ).then(() => this.$emit("refresh"));
    },
  },
  computed: {
  },
};
</script>

<style scoped></style>
