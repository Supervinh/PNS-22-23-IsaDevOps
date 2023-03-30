import {Injectable} from '@nestjs/common';

import {PaymentDto} from './dto/payment.dto';
import {PaymentRejectedException} from './exceptions/payment-rejected-exception';

@Injectable()
export class AppService {

    private static readonly magicKey: string = '896983'; // ASCII code for 'YES'

    private transactions: Array<PaymentDto>;

    constructor() {
        this.transactions = [];
    }

    findAll(): PaymentDto[] {
        return this.transactions;
    }

    pay(paymentDto: PaymentDto): PaymentDto {
        console.log('Receive a new payment');
        if (paymentDto.creditCard.includes(AppService.magicKey)) {
            console.log('Payment of ' + paymentDto.amount + ' accepted');
            return paymentDto;
        } else {
            throw new PaymentRejectedException(paymentDto.amount);
        }
    }

}
