public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    private List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.categoryText.setText(transaction.category);
        holder.amountText.setText("₦" + transaction.amount);

        if ("expense".equals(transaction.type))
            holder.amountText.setTextColor(Color.RED);
        else
            holder.amountText.setTextColor(Color.parseColor("#008000"));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText, amountText;

        public ViewHolder(View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
            amountText = itemView.findViewById(R.id.amountText);
        }
    }
}
