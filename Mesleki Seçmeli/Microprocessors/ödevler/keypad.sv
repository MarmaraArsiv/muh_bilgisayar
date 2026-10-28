module ctrl_keypad4x4
  (input logic         i_clk,
   input logic         i_ack,
   input logic         i_rselect,
   input logic [3:0]   i_col_data,
   output logic [3:0]  o_row_data,
   output logic [15:0] o_data);
   localparam SCANNER_CKE_DIVISOR = 100000;
   logic        ready;
   logic [3:0]  data;
   logic        scanner_tick;
   logic [23:0] scanner_tick_counter;
   logic [3:0]  key;
   logic        key_pressed;
   logic [1:0]  key_pressed_buffer;
   logic [3:0]  col_data [4];
   logic        row_active [4];
   logic [3:0]  row_active_debounced;
   logic [11:0] row_active_buffer [4];
   logic        consecutive_0s [4];
   logic        consecutive_1s [4];
   always_ff @(posedge i_clk)
     begin: SCANNER_SCAN
        if (scanner_tick) begin
           o_row_data <= {o_row_data[2:0], o_row_data[3]};
           case (o_row_data)
             4'b1110: {row_active[0], col_data[0]} <= {~(&i_col_data), i_col_data};
             4'b1101: {row_active[1], col_data[1]} <= {~(&i_col_data), i_col_data};
             4'b1011: {row_active[2], col_data[2]} <= {~(&i_col_data), i_col_data};
             4'b0111: {row_active[3], col_data[3]} <= {~(&i_col_data), i_col_data};
           endcase
        end
     end
   always_ff @(posedge i_clk)
     begin: SCANNER_DEBOUNCE
        if (scanner_tick) begin
           for (int i = 0; i < 4; i++) begin
              row_active_buffer[i] <= {row_active_buffer[i][10:0], row_active[i]};
              if (consecutive_1s[i]) begin
                 row_active_debounced[i] <= 1;
              end
              if (consecutive_0s[i]) begin
                 row_active_debounced[i] <= 0;
              end
           end
        end
     end
   always_ff @(posedge i_clk)
     begin: INTERRUPT
        key_pressed_buffer <= {key_pressed_buffer[0], key_pressed};
        if ((key_pressed_buffer == 2'b01) && !ready) begin
           data <= key;
           ready <= 1;
        end else if (i_ack && ready) begin
           ready <= 0;
        end
     end
   always_ff @(posedge i_clk)
     begin: SCANNER_CKE
        if (scanner_tick_counter == SCANNER_CKE_DIVISOR) begin
           scanner_tick <= 1;
           scanner_tick_counter <= 0;
        end else begin
           scanner_tick <= 0;
           scanner_tick_counter <= scanner_tick_counter + 1;
        end
     end
   always_comb
     begin: SCANNER_DECODE
        if (row_active_debounced[0]) begin
           case (col_data[0])
             4'b1110: key = 4'h1;
             4'b1101: key = 4'h2;
             4'b1011: key = 4'h3;
             4'b0111: key = 4'hA;
             default: key = 4'h0;
           endcase
        end else if (row_active_debounced[1]) begin
           case (col_data[1])
             4'b1110: key = 4'h4;
             4'b1101: key = 4'h5;
             4'b1011: key = 4'h6;
             4'b0111: key = 4'hB;
             default: key = 4'h0;
           endcase
        end else if (row_active_debounced[2]) begin
           case (col_data[2])
             4'b1110: key = 4'h7;
             4'b1101: key = 4'h8;
             4'b1011: key = 4'h9;
             4'b0111: key = 4'hC;
             default: key = 4'h0;
           endcase
        end else if (row_active_debounced[3]) begin
           case (col_data[3])
             4'b1110: key = 4'hE;
             4'b1101: key = 4'h0;
             4'b1011: key = 4'hF;
             4'b0111: key = 4'hD;
             default: key = 4'h0;
           endcase
        end else begin
           key = 4'b0000;
        end
     end
   always_comb
     begin
        for (int i = 0; i < 4; i++) begin
           consecutive_0s[i] = ~|row_active_buffer[i];
           consecutive_1s[i] = &row_active_buffer[i];
        end
     end
   always_comb
     begin
        unique case (i_rselect)
          1'b0: o_data = 16'(data);
          1'b1: o_data = 16'(ready);
        endcase
     end
   assign key_pressed = |row_active_debounced;
   initial
     begin
        o_row_data = 4'b1110;
        ready = 0;
     end
endmodule