export interface AccountDTO {
    id: string;
    type: 'SavingAccount' | 'CurrentAccount';
    balance: number;
    createdAt: string;       // ou Date si vous convertissez
    status?: string | null;
    
    // Spécifique SavingAccount
    interestRate?: number;

    // Spécifique CurrentAccount
    overDraft?: number;

    // Infos client (si nécessaire)
    customerDTO: {
        id: number;
        name: string;
        email: string;
    };
}
