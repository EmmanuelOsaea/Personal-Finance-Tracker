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
