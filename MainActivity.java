DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("transactions");

dbRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        double totalIncome = 0, totalExpense = 0;
        for (DataSnapshot data : snapshot.getChildren()) {
            String type = data.child("type").getValue(String.class);
            double amount = data.child("amount").getValue(Double.class);

            if ("income".equals(type)) totalIncome += amount;
            else if ("expense".equals(type)) totalExpense += amount;
        }

        double balance = totalIncome - totalExpense;

        incomeTextView.setText("₦" + totalIncome);
        expenseTextView.setText("₦" + totalExpense);
        balanceTextView.setText("₦" + balance);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
    }
});



RecyclerView recyclerView = findViewById(R.id.transactionsRecyclerView);
List<Transaction> transactionList = new ArrayList<>();
TransactionAdapter adapter = new TransactionAdapter(transactionList);
recyclerView.setAdapter(adapter);
recyclerView.setLayoutManager(new LinearLayoutManager(this));

DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("transactions");
dbRef.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        transactionList.clear();
        for (DataSnapshot data : snapshot.getChildren()) {
            Transaction t = data.getValue(Transaction.class);
            if (t != null) transactionList.add(t);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(MainActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
    }
});


EditText categoryInput = findViewById(R.id.categoryInput);
EditText amountInput = findViewById(R.id.amountInput);
Spinner typeSpinner = findViewById(R.id.typeSpinner);
Button addButton = findViewById(R.id.addButton);

DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("transactions");

addButton.setOnClickListener(v -> {
    String category = categoryInput.getText().toString().trim();
    String amountStr = amountInput.getText().toString().trim();
    String type = typeSpinner.getSelectedItem().toString().toLowerCase();

    if (category.isEmpty() || amountStr.isEmpty()) {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        return;
    }

    double amount = Double.parseDouble(amountStr);
    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    Transaction transaction = new Transaction(type, amount, category, date);

    String key = dbRef.push().getKey();
    if (key != null) dbRef.child(key).setValue(transaction);

    categoryInput.setText("");
    amountInput.setText("");

    Toast.makeText(this, "Transaction added!", Toast.LENGTH_SHORT).show();
});
        
